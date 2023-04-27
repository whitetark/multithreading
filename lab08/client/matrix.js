const clientDiv = document.getElementById('client')
const serverDiv = document.getElementById('server')
let matrixSize = 3000
document.getElementById('size').innerText = matrixSize

async function generateOnClient() {
  event.preventDefault
  let matrix1 = generateRandomMatrix(matrixSize)
  let matrix2 = generateRandomMatrix(matrixSize)
  var obj = {
    Matrix1: matrix1,
    Matrix2: matrix2,
  }
  var xhttp = new XMLHttpRequest()
  const startTime = Date.now()
  xhttp.open('POST', 'https://localhost:7052/Matrix/multiply/', true)
  xhttp.setRequestHeader('Content-type', 'application/json')
  xhttp.send(JSON.stringify(obj))
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      var response = JSON.parse(this.responseText)
      const endTime = Date.now()
      let time = endTime - startTime
      clientDiv.innerText = time
      console.log('Matrix Generated On Client And Time is ', time)
    }
  }
}

async function generateOnServer() {
  event.preventDefault
  var xhttp = new XMLHttpRequest()
  const startTime = Date.now()
  xhttp.open('GET', 'https://localhost:7052/Matrix/generate/' + matrixSize, true)
  xhttp.send()
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      var response = JSON.parse(this.responseText)
      const endTime = Date.now()
      let time = endTime - startTime
      serverDiv.innerText = time
      console.log('Matrix Generated On Server And Time is ', time)
    }
  }
}

function generateRandomMatrix(size) {
  var matrix = []
  for (var i = 0; i < size; i++) {
    matrix[i] = []
    for (var j = 0; j < size; j++) {
      matrix[i][j] = Math.floor(Math.random() * 100)
    }
  }
  return matrix
}

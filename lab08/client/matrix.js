generatedOnServer()
generatedOnClient()

function generatedOnServer() {
  const startTime = Date.now()
  fetch('https://localhost:7052/Matrix', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then((response) => response.json())
    .then(async (result) => {
      const endTime = Date.now()
      let time = endTime - startTime
      console.log('Generated On Server Time: ', time)
    })
}

async function generatedOnClient() {
  const matrixSize = 1000

  const matrixA = generateRandomMatrix(matrixSize)
  const matrixB = generateRandomMatrix(matrixSize)

  const requestBody = {
    matrix1: matrixA,
    matrix2: matrixB,
  }

  const data = JSON.stringify(requestBody)

  const startTime = Date.now()
  const response = await fetch('https://localhost:7052/Matrix', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: data,
  })
  const endTime = Date.now()
  let time = endTime - startTime
  console.log('Generated On Client Time: ', time)
}

function generateRandomMatrix(size) {
  var matrix = []
  for (var i = 0; i < size; i++) {
    matrix[i] = []
    for (var j = 0; j < size; j++) {
      matrix[i][j] = Math.floor(Math.random() * 100) // генерація випадкового числа від 0 до 99
    }
  }
  return matrix
}

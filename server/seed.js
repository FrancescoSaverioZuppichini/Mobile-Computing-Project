var mongoose = require('mongoose')
var seeder = require('mongoose-seed');
const User = require('./models/User')

DB_URL = 'mongodb://localhost/mobile_computing'

seeder.connect(DB_URL, function () {
  // Load Mongoose models
  seeder.loadModels([
    './models/User.js'
  ])
  // Clear specifixsed collections
  seeder.clearModels(['User'], function () {
    // Callback to populate DB once collections have been cleared
    seeder.populateModels(data, function () {
      seeder.disconnect();
    })
  })
})

User.collection.dropIndexes(function (err, results) {
  // Handle errors
  if(!err){
    console.log('drop indexes')
  }
});



const data = [{
  'model': 'User',
  'documents': [{
      email: 'a@b.com',
      password: 'a',
      role: 'VOLUNTEER',
      medicInfo: {
        blood: 'O+'
      },
      location: {
        type: 'Point',
        coordinates: [8.957484199999953, 46.011095]
      }
    },
    {
      email: 'zuppi@test.com',
      password: 'ivan',
      role: 'USER',
      medicInfo: {
        blood: 'A+'
      },
      location: {
        type: 'Point',        
        coordinates: [8.96078866, 6.0108692]
      }
    },
    {
      email: 'thomas@imanol.com',
      password: 'jurgen',
      role: 'VOLUNTEER',
      medicInfo: {
        blood: 'AB+'
      },
      location: {
        type: 'Point',        
        coordinates: [8.96078866, 46.0108692]
      }
    }
  ]
}]

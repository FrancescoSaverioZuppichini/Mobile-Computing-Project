var mongoose = require('mongoose')
var seeder = require('mongoose-seed');

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
        latitude: 46.011095,
        longitude: 8.957484199999953
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
        latitude: 46.0108692,
        longitude: 8.96078866
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
        latitude: 46.0108692,
        longitude: 8.96078866
      }
    }
  ]
}]

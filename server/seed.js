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
      email: 'alessia@95Xd.com',
      password: 'francesco<3',
      role: 'VOLUNTEER',
      medicInfo: {
        blood: 'O+'
      },
      position: {
        lat: 46.011095,
        long: 8.957484199999953
      }
    },
    {
      email: 'zuppi',
      password: 'ivan',
      role: 'USER',
      medicInfo: {
        blood: 'A+'
      },
      position: {
        lat: 46.0108692,
        long: 8.96078866
      }
    },
    {
      email: 'thomas@imanol.com',
      password: 'jurgen',
      role: 'VOLUNTEER',
      medicInfo: {
        blood: 'AB+'
      },
      position: {
        lat: 46.0108692,
        long: 8.96078866
      }
    }
  ]
}]

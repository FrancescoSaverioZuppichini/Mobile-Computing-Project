var mongoose = require('mongoose')
var Schema = mongoose.Schema
var bcrypt = require('bcrypt')

var userSchema = new Schema({
  
  email: {
    required: true,
    type: String
  },
  password: {
    type: String,
    required: true
  },
  role: {
    type: String,
    enum: ['USER', 'VOLUNTEER'],
    default: 'USER'
  },
  medicInfo: {
    blood: {
      type: String,
      enum: ['O+', 'A+', 'B+', 'AB+', 'O-', 'A-', 'B-', 'AB']
    }
  },
  preferences: {
    audioStream: {
      type: Boolean,
      default: false
    },
    videoStream: {
      type: Boolean,
      default: false
    },
    autoEmergencyCall: {
      shouldCall: {
        type: Boolean,
        default: true
      },
      number: {
        type: String
      }
    },
    radiusInMeters: {
      type: Number,
      default: 3000.0
    }
  },
  location: {
    type: {
      type: String,
      default: 'Point'
    },
    coordinates: [Number] //longitude, latitude
  }
})

// hash password before store it 
userSchema.pre("save", async function (next) {
  try {
    var error = this.validateSync()

    if (error) throw new Error(error)

    if (this.isModified('password')){
      console.log("DIOCANENENNENE")
    const hash = await bcrypt.hashSync(this.password, 10)
    this.password = hash
    }

    next()

  } catch (err) {
    next(err)
  }
})

userSchema.methods.passwordIsValid = (toCheck, valid) => bcrypt.compareSync(toCheck, valid)

userSchema.methods.getNeighbors = function(from) {
  const coordinates = this.location.coordinates

  return this.model('User').aggregate(
    [
        { "$geoNear": {
            "near": {
                "type": "Point",
                "coordinates": coordinates
            },
            "distanceField": "dist",
            "spherical": true,
            "maxDistance": 10000
        }}
    ])
}

userSchema.index({
  location: '2dsphere'
})


module.exports = mongoose.model('User', userSchema);;

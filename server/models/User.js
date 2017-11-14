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
      enum: [ 'O+', 'A+', 'B+', 'AB+', 'O-', 'A-', 'B-', 'AB']
    } 
  }
})
// hash password before store it 
userSchema.pre("save", async function (next) {

  try {
    var error = this.validateSync()

    if(error) throw new Error(error)
    
    const hash = await bcrypt.hashSync(this.password, 10)

    this.password = hash
    next()

  } catch (err) {
    err.status = 400
    next(err)
  }
})

userSchema.methods.passwordIsValid = (toCheck, valid) => bcrypt.compareSync(toCheck, valid)

// userSchema.methods.sanitaze()

module.exports = mongoose.model('User', userSchema);

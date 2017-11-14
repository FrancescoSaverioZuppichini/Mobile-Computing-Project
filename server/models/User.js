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
    required: true,
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
  if (!this.isModified("password")) {
    return next();
  }
  try {
    const hash = await bcrypt.hashSync(this.password, 11)
    this.password = hash
    next()

  } catch (err) {
    next(err)
  }

})

userSchema.methods.passwordIsValid = (toCheck, valid) => bcrypt.compareSync(toCheck, valid)

// userSchema.methods.sanitaze()

module.exports = mongoose.model('User', userSchema);

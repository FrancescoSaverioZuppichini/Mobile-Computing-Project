var express = require('express');
var router = express.Router();

var jwt = require('jsonwebtoken')

const User = require('../models/User')

const errors = { EMAIL_ALREADY_IN_USE: { message: "Email already used" }, PASSWORD_NOT_VALID: { message: 'Password or Email not valid'} }

class AuthError extends Error {
  constructor(status, message){
    this.status = status || 500
    this.message = message
  }
}

// login
router.put('/', async(req, res, next) => {
  
  try {
    const email = req.body.email
    const password = req.body.password

    const user = await User.findOne({ email : email })

    if(!user) throw errors.PASSWORD_NOT_VALID
    
    if(!user.passwordIsValid(password, user.password)) throw errors.PASSWORD_NOT_VALID

    req.user = user
  
    next()

  } catch (err) {
    console.log(err)
    next(err)
  }
})
// generate a token
router.put('/', async (req, res, next) => {
    res.send(jwt.sign({data: req.user  }, 'alessia', { expiresIn: '1h' }))
})
// normal username/email registration
router.post('/', async(req, res, next) => {
  try {
    const userWithThatEmailAlreadyExists = await User.findOne({ email: req.body.email }) 

    if(userWithThatEmailAlreadyExists) throw errors.EMAIL_ALREADY_IN_USE

    const newUser = await User(req.body).save()

    res.send(newUser);

  } catch (err) {
    console.log(err)
    next(err)
  }
})

module.exports = router
var express = require('express');
var jwt = require('jsonwebtoken-refresh')
var User = require('../models/User')

var router = express.Router();
// custom errors
const errors = {
  EMAIL_ALREADY_IN_USE: {
    message: "Email already used"
  },
  PASSWORD_NOT_VALID: {
    message: 'Password or Email not valid'
  }
}
// login
router.put('/', async(req, res, next) => {
  try {
    const email = req.body.email
    const password = req.body.password

    const user = await User.findOne({
      email: email
    })

    if (!user) throw errors.PASSWORD_NOT_VALID

    if (!user.passwordIsValid(password, user.password)) throw errors.PASSWORD_NOT_VALID

    req.user = user

    next()

  } catch (err) {
    next(err)
  }
})
// generate a token
router.put('/', async(req, res, next) => {
  res.json({
    token: jwt.sign({
      data: req.user
    }, process.env.TOKEN_SECRET, {
      expiresIn: '12h'
    })
  })
})
// normal username/email registration
router.post('/', async(req, res, next) => {
  try {
    const userWithThatEmailAlreadyExists = await User.findOne({
      email: req.body.email
    })

    if (userWithThatEmailAlreadyExists) throw errors.EMAIL_ALREADY_IN_USE

    const newUser = await User(req.body).save()

    res.status(201).json(newUser)

  } catch (err) {
    next(err)
  }
})

module.exports = router

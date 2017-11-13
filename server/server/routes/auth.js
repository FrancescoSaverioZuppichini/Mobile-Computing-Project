var express = require('express');
var router = express.Router();

var jwt = require('jsonwebtoken')

const User = require('../models/User')
// login
router.put('/', async(req, res, next) => {
  
  try {
    const email = req.body.email
    const password = req.body.password

    const user = await User.findOne({ email : email })
    // 
    if(!user.passwordIsValid(password, user.password)) throw Error('password not valid')

    req.user = user
  
    next()

  } catch (err) {

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
    const newUser = await User(req.body).save()

    res.send(newUser);

  } catch (err) {
    console.log(err)
    next(err)
  }
})

module.exports = router
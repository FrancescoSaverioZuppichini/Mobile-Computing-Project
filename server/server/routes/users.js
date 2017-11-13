var express = require('express');
var router = express.Router();

const User = require('../models/User')
/* GET users listing. */
router.get('/', async(req, res, next) => {
  const users = await User.find()
  res.send(users);
});

router.get('/:id', async(req, res, next) => {
  try {
    const user = await User.findById(req.params.id)
    res.send(user);
    
  } catch (err) {
    next(err)
  }
});

router.put('/:id', async(req, res, next) => {
  try {
    const user = await User.findById(req.params.id)

    const updatedUser = await user.save(req.body)

    res.send(updatedUser);

  } catch (err) {
    next(err)
  }
});

router.post('/', async(req, res, next) => {
  try {
    const user = await User.findById(req.params.id)

    const newUser = await user.save(req.body)

    res.send(newUser);

  } catch (err) {
    next(err)
  }
});


module.exports = router;

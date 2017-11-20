var user = { email: 'test@com', password: '123' }

module.exports = function (chai, server, should) {
    it('it should create a new user', (done) => {
      chai.request(server)
        .post('/auth/')
        .send(user)
        .end(function (err, res) {
          res.should.have.status(201)
          res.should.have.be.a('object')
          res.body.should.have.property('email').eql(user.email)
          done()
        })
    })
    it('it should not create a user with the same email', (done) => {
      chai.request(server)
        .post('/auth/')
        .send(user)
        .end(function (err, res) {
          res.should.not.have.status(200)
          res.should.have.be.a('object')
          res.body.should.have.property('message')
          done()
        })
    })
    it('it should return a token if the credential are correct', (done) => {
      chai.request(server)
        .put('/auth/')
        .send(user)
        .end(function (err, res) {
          res.should.have.status(200)
          res.should.have.be.a('object')
          res.body.should.have.property('token')
          // store it
          user.token = res.body.token
          done()
        })
    })
    it('it should not return a token if no credential', (done) => {
      chai.request(server)
        .put('/auth/')
        .send({})
        .end(function (err, res) {
          res.should.not.have.status(200)
          res.should.have.be.a('object')
          res.body.should.not.have.property('token')
          done()
        })
    })
    it('it should reflesh a token if the old one is valid', (done) => {
      chai.request(server)
        .put('/api/users/refresh/me')
        .set('Authorization', 'Bearer ' + user.token)
        .end(function (err, res) {
          res.should.have.status(200)
          res.should.have.be.a('object')
          res.body.should.have.property('token')
          done()
        })
    })
    it('it should not return a token if the email is wrong', (done) => {
      chai.request(server)
        .put('/auth/')
        .send({password: user.password, email: 'foo'})
        .end(function (err, res) {
          res.should.not.have.status(200)
          res.should.have.be.a('object')
          res.body.should.not.have.property('token')
          done()
        })
    })
    it('it should not return a token if the password is wrong', (done) => {
      chai.request(server)
        .put('/auth/')
        .send({email: user.email, password: 'foo'})
        .end(function (err, res) {
          res.should.not.have.status(200)
          res.should.have.be.a('object')
          res.body.should.not.have.property('token')
          done()
        })
    })
  }
  
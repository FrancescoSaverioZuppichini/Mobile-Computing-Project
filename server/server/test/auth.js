var user = { email: 'test@com', password: '123' }

module.exports = function (chai, server, should) {
    it('it should create a new user', (done) => {
      chai.request(server)
        .post('/auth/')
        .send(user)
        .end(function (err, res) {
          res.should.have.status(200)
          res.should.have.be.a('object')
          res.body.should.have.property('email').eql(user.email)
          done()
        });
    });
  }
  
module.exports = [
	{
		context: [ '/api/**' ],
		target: 'http://localhost:8080',
		secure: false,
		logLevel: 'debug'
	}
]

//ng serve --proxy-config proxy.config.js

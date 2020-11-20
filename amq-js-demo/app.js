var container = require('rhea');

const destination = process.env.BROKER_DESTINATION || 'example';

container.once('connection_open', function (context) {
    context.connection.open_receiver(destination);
    context.connection.open_sender(destination);
});

container.on('message', function (context) {
    console.log('Message received: ' + context.message.body);
});

var counter = 0;

container.once('sendable', function (context) {
    function send() {
        if (context.sender.sendable()) {
            context.sender.send({body: JSON.stringify(
                { 
                    id: ++counter, 
                    text: 'Hello World!',
                    timestamp: Date.now()
                }
            )});
        }
        setTimeout(send, 10000);
    }
    send();
});

container.on('disconnected', function (context) {
    console.log('disconnected');
});

const hostname = process.env.BROKER_HOSTNAME || 'localhost'
const port = process.env.BROKER_PORT || 5672

var conn = container.connect({'host':hostname,'port':port, 'reconnect':true});

const request = require('request')

const cache = {};

function ping() {
    request('http://localhost:8080/api/machines/printos', function (error, response, body) {
        const machines = JSON.parse(response.body);
        machines.forEach(machine => {
            if (!cache[machine.id]) {
                cache[machine.id] = machine.resourceLevel;
            } else {
                if (cache[machine.id] != machine.resourceLevel) {
                    stateChange(machine);
                }
            }
        })
    });
}

function stateChange(machine) {
    console.log(`State change detected for ${machine.pressName}`)
    cache[machine.id] = machine.resourceLevel;
}


////

setInterval(ping, 2000);

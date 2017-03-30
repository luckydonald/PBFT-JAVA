PBFT
====
[![Build Status](https://travis-ci.org/luckydonald/PBFT-JAVA.svg?branch=master)](https://travis-ci.org/luckydonald/PBFT-JAVA) [![Coverage Status](https://coveralls.io/repos/github/luckydonald/PBFT-JAVA/badge.svg?branch=master)](https://coveralls.io/github/luckydonald/PBFT-JAVA?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/ee3937a213e447a79d36f5cc0597d046)](https://www.codacy.com/app/luckydonald/PBFT-JAVA?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=KathrynJaneway/PBFT-JAVA&amp;utm_campaign=Badge_Grade)


Node configuration
==================
This offers different ways to load configuration and information about the other nodes.

It will try to load the information in the following order:

1. [Configuration file](#configuration-file)
2. [Docker & Environment variables](#docker--environment-variables)
3. [Dummy Configuration](#dummy-configuration)

The detection is made in class `DockerusAuto`.

Docker & Environment variables
------------------------------
If this is launched via docker-compose, and is multiplied using `scale`,
it will already grab all needed info from docker, and the following **Environment variables**

- `HOSTNAME` own host name (should be given from the system)
- `API_HOST` where the api node is. _Example: `http://localhost:8080/api`_
- `SENSOR_SIMULATE` set to `1` to enable reading from the `DS1820` sensor (tested on Raspberry Pi)

This happens in the class `Dockerus`.


Configuration file
------------------
If you specify a `config.json` file however, that one will be used:

```python
{
    "node_hosts": ["192.168.2.8", "192.168.2.9", "192.168.2.10", "192.168.2.11"],
    "own_host": "192.168.2.8",  # If not given: Falls back to the local socket ip address, which might be wrong!
    "api_host": "http://example.com/"  # If you have this entry, it overwrites $API_HOST env variable.  Or set to null, to disable.
    "sensor_simulate": false  # If you have this entry, it overwrites $SENSOR_SIMULATE env variable.
}
```
This is handled in the class `DockerusFile`.


Dummy Configuration
-------------------
It is not possible to use the PBFT algorithm with this.
This is the fallback so unit tests can still be executed.
See the class `DockerusDummy`.




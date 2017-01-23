PBFT
====
[![Build Status](https://travis-ci.org/luckydonald/PBFT-JAVA.svg?branch=master)](https://travis-ci.org/luckydonald/PBFT-JAVA) [![Coverage Status](https://coveralls.io/repos/github/luckydonald/PBFT-JAVA/badge.svg?branch=master)](https://coveralls.io/github/luckydonald/PBFT-JAVA?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/ee3937a213e447a79d36f5cc0597d046)](https://www.codacy.com/app/luckydonald/PBFT-JAVA?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=KathrynJaneway/PBFT-JAVA&amp;utm_campaign=Badge_Grade)

Environment variables
---------------------

- `HOSTNAME` own host name (should be given from the system)
- `API_HOST` where the api node is.


Configuration file
------------------
If this is launched via docker-compose, and is multiplied using `scale`,
it will already grab all needed info from docker.

If you specify a `config.json` file however, that one will be used:

```python
{
    "node_hosts": ["192.168.2.8", "192.168.2.9", "192.168.2.10", "192.168.2.11"],
    "own_host": "192.168.2.8",  # If not given: Falls back to the local socket ip address, which might be wrong!
    "api_host": "http://example.com/"  # If you have this entry, it overwrites $API_HOST env variable.  Or null, to disable.
}
```
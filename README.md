## API Endpoints
The API previously provided by the EMI plugin has been split to its own plugin so that updates to the API can be maintained without breaking the primary systems. The following endpoints are available:
- **Player Stats** - `/stats/:uuid` - GET
- **Player Advancements** - `/advs/:uuid` - GET
- **Whitelist command** - `/cmd/:wlcommand/:player?=:token` - POST

All calls return JSON.
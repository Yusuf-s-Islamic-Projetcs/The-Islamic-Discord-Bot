# The Islamic Bot

This is a bot that can be used to get information about Islamic events, such as prayer times, qibla direction, and more. It is currently in development, and is not yet ready for use.


## Installation

To add this bot to your server, click [here](https://discord.com/api/oauth2/authorize?client_id=1083467693899915337&permissions=1024353368129&scope=bot%20applications.commands). You will need to have the `Manage Server` permission to add the bot to your server.

## Contributing

To contribute you will not create a config.json file in the root directory. This file should contain the following:

```json
{
    "TOKEN": "YOUR_TOKEN_HERE",
    "DB_URL" : "YOUR_DB_URL_HERE",
    "DB_USER" : "YOUR_DB_NAME_HERE",
    "DB_PASSWORD" : "YOUR_DB_PASS_HERE"
}
```

postgresql is used for the database.
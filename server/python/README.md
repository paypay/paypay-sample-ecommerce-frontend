# Python SDK Implementation

This is a python based implementation of PayPay's SDK. For the demonstration purpose we have used a simple Flask server with polling library to create QR code link and to check the order status of a QR code

### Install Requirements

```sh
$ cd server/python
$ pip install -r requirements.txt
```

### Add API Keys to environment

```sh
$ export API_KEY="REPLACE_WITH_YOUR_API_KEY"
$ export API_SECRET="REPLACE_WITH_YOUR_SECRET_KEY" 
```

### Run Flask API server
```sh
$ flask run
```
You should now have the API server running on http://localhost:5000


## Heroku Deployment

## PayPay Ecom Sample App - One click Install doc

The idea here is that you have a single git repository, but multiple Heroku apps. In other words, you want to share a single git repository to power multiple Heroku apps. So, for each app you need this buildpack, and for each app, you need to set a config variable named PROCFILE to the location where the procfile is for that app. As an example:


## Native support for buildpacks

Buildpacks are composed of a set of scripts depending on the programming language used. These scripts are responsible for transforming the deployed code before being executed on the dyno manager. (The scripts gather the dependencies, which then outputs generated code. When pushing to Heroku, the code is received by the slug compiler which transforms the repo into a slug and off to a dyno for execution).


```
heroku create -a fe
heroku create -a be
heroku buildpacks:add -a fe https://github.com/heroku/heroku-buildpack-multi-procfile
heroku buildpacks:add -a be https://github.com/heroku/heroku-buildpack-multi-procfile
heroku config:set -a fe PROCFILE=Procfile
heroku config:set -a be PROCFILE=backend/Procfile
git push https://git.heroku.com/example-1.git HEAD:master
git push https://git.heroku.com/example-2.git HEAD:master
```



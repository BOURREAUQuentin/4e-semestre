from flask import Flask
from flask_bootstrap import Bootstrap5
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager
import os.path
app = Flask(__name__)
app.config['BOOTSTRAP_SERVE_LOCAL']=True
Bootstrap = Bootstrap5(app)

def mkpath(p):
    return os.path.normpath(os.path.join(os.path.dirname(__file__),p))

app.config['SQLALCHEMY_DATABASE_URI'] = ('sqlite:///'+mkpath('../myapp.db'))
db = SQLAlchemy(app)
app.config['SECRET_KEY'] = "340d39fa-8a19-4e2d-99b0-96f45aa52f64"
login_manager = LoginManager(app)
login_manager.login_view = 'login'

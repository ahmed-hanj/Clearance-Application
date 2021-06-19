from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask import request

app=Flask(__name__)

app.config["SQLALCHEMY_DATABASE_URI"]='sqlite:///clearance.db'
db=SQLAlchemy(app)




#############################
class Student(db.Model):
    std_id=db.Column(db.Integer,primary_key=True)
    std_name=db.Column(db.String(30),nullable=False)
    std_degree=db.Column(db.String(30),nullable=False)
    std_dept=db.Column(db.String(30),nullable=False)
    def __repr__(self):
        return f"{self.std_name}-{self.std_id}-{self.std_degree}-{self.std_dept}"




@app.route('/')
def get_index():
    return 'Index Page'


@app.route('/student')
def get_student():
    std_DATA=Student.query.all()
    values=[]
    for data in std_DATA:
        std_dic={"sn":data.std_name,"sid":data.std_id,"deg":data.std_degree, "dept":data.std_dept}
        values.append(std_dic)
    return {"Student":values}


@app.route('/student',methods=['POST'])
def add_student():
    std=Student(std_id=request.form['sid'],std_name=request.form['sn'],std_degree=request.form['deg'],std_dept=request.form['dept'])
    db.session.add(std)
    db.session.commit()
    return {'id':std.std_id}


@app.route('/student/<id>',methods=['DELETE'])
def delete_sdata(id):
    sdata=Student.query.get(id)
    if sdata is None:
        return {"error":"Not Found"}
    db.session.delete(sdata)
    db.session.commit()
    return id






if(__name__)=="__main__":
    app.run(host='0.0.0.0')




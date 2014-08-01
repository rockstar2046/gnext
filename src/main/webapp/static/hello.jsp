<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">
  <head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<%@ include file="include.jsp"%>
	<title></title>
  </head>
  <body>


	<div class="container">

	  <div class="well well-lg">
	  I'm in.
	  </div>
	  <dl class="dl-horizontal">
		<dt>我诶水电费.</dt>
		<dd>说了玩的 <span class="badge">1618</span></dd>
	  </dl>
	  <h1> bootstrap is not bad! <small>good</small></h1>

	  <p class="text-muted">text-muted</p>
	  <p class="text-primary">text-primary</p>
	  <p class="text-success">text-success</p>
	  <p class="text-info">text-info</p>
	  <p class="text-warning">text-warning</p>
	  <p class="text-danger">text-danger</p>

	  <blockquote>
		<p>This some bootstrap tutorail, you can get this from rockagn.com</p>
		<small>rockagen <cite title="AGEN">.agen</cite></small>
	  </blockquote>

	  <br/>

<pre> print("hello");</pre>

	  <div class="table-responsive">
		<table class="table table-hover">
		  <tr>
			<th>NAME</th>
			<th>AGE</th>
			<th>ADDR</th>
			<th>EMAIL</th>
		  </tr>
		  <tr class="success">
			<td>AGEN</td>
			<td>20</td>
			<td>USA</td>
			<td>AGEN@ROCKAGEN.COM</td>
		  </tr>
		  <tr class="warning">
			<td>AGEN</td>
			<td>20</td>
			<td>USA</td>
			<td>AGEN@ROCKAGEN.COM</td>
		  </tr>
		  <tr class="danger">
			<td>AGEN</td>
			<td>20</td>
			<td>USA</td>
			<td>AGEN@ROCKAGEN.COM</td>
		  </tr>
		  <tr  class="active">
			<td>AGEN</td>
			<td>20</td>
			<td>USA</td>
			<td>AGEN@ROCKAGEN.COM</td>
		  </tr>
		</table>
	  </div>

	  <form role="form" clas="form-horizontal" action="">

		<div class="form-group">
		  <label class="col-lg-2 control-label">Email</label>
		  <div class="col-lg-10">
			<p class="form-control-static">email@example.com</p>
		  </div>
		</div>


		<div class="form-group">
		  <label class="col-sm-2 control-label" for="inputEmail">Email</label>
		  <div class="col-sm-10">
			<input type="email" class="form-control" placeholder="Email" />
		  </div>
		</div>

		<div class="form-group">
		  <label class="col-sm-2 control-label" for="inputPhone">Phone</label>
		  <div class="col-sm-10">
<div class="input-group">
  <span class="input-group-addon">+86</span>
<input type="text" class="form-control input-lg" placeholder="Phone" />
  <span class="input-group-addon">CHINA</span>
 
</div>
			
		  </div>
		</div>

		<div class="form-group">
		  <label class="col-sm-2 control-label" for="inputPassword">Password</label>
		  <div class="col-sm-10">
			<input type="password" class="form-control" placeholder="password" />
		  </div>

		</div>

		<div class="form-group">
		  <label class="col-sm-2 control-label" for="select">Sellect</label>
		  <div class="col-sm-10">
			<select id="" class="form-control" name="">
			  <option value="">USA</option>
			  <option value="">CHINA</option>
			  <option value="">JAPAN</option>
			  <option value="">KOREA</option>
			</select>
		  </div>

		</div>

		<div class="form-group">
		  <label class="col-sm-2 control-label" for="inputText">Textarea</label>
		  <div class="col-sm-10">
			<textarea class="form-control" rows="3"></textarea>
		  </div>

		</div>

		<div class="form-group">
		  <label class="col-sm-2 control-label" for="inputText">Disabled input</label>
		  <div class="col-sm-10">
		 <input type="text" id="disabledTextInput" class="form-control" disabled placeholder="Disabled input">
	   <span class="help-block">自己独占一行或多行的块级帮助文本。</span>
	   </div>

		</div>



		
	<div class="form-group">

		  <div class="col-sm-offset-2 col-sm-10">
			<button type="button" class="btn btn-primary btn-lg btn-block">primary</button>
			<button type="button" class="btn btn-success btn-sm">success</button>
			<button type="button" class="btn btn-info btn-xs">info</button>
			<button type="button" class="btn btn-warning">warning</button>
			<button type="button" class="btn btn-danger" disabled="disabled">danger</button>
			<button type="button" class="btn btn-link">link</button>
			<button type="button" class="close" aria-hidden="true">&times;</button>
		  </div>


		</div>


		<div class="form-group">

		  <div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn btn-default">Sign in</button>
		  </div>


		</div>

	  </form>
	</div>
<div class="progress progress-striped active">
  <div class="progress-bar"  role="progressbar" aria-valuenow="90" aria-valuemin="0" 
	aria-valuemax="100" style="width: 45%">
    <span class="sr-only">45% Complete</span>
  </div>
</div>

<div class="alert alert-warning alert-dismissable">
  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
  <strong>Warning!</strong> Best check yo self, you're not looking too good.
</div>



<div class="panel panel-primary">

 <!-- Default panel contents -->
  <div class="panel-heading"> panel-primary</div>
  <div class="panel-body">
    <p>...</p>
  </div>

  <!-- List group -->
  <ul class="list-group">
    <li class="list-group-item">Cras justo odio</li>
    <li class="list-group-item">Dapibus ac facilisis in</li>
    <li class="list-group-item">Morbi leo risus</li>
    <li class="list-group-item">Porta ac consectetur ac</li>
    <li class="list-group-item">Vestibulum at eros</li>
  </ul>

</div>
<div class="panel panel-success">
  
 
 <!-- Default panel contents -->
  <div class="panel-heading"> panel-success</div>
  <div class="panel-body">
    <p>...</p>
  </div>

  <!-- List group -->
  <ul class="list-group">
    <li class="list-group-item">Cras justo odio</li>
    <li class="list-group-item">Dapibus ac facilisis in</li>
    <li class="list-group-item">Morbi leo risus</li>
    <li class="list-group-item">Porta ac consectetur ac</li>
    <li class="list-group-item">Vestibulum at eros</li>
  </ul>
</div>
<div class="panel panel-info">
   <!-- Default panel contents -->
   <div class="panel-heading"> panel-info</div>
  <div class="panel-body">
    <p>...</p>
  </div>
 </div>
 <div class="panel panel-warning">
   <div class="panel-heading">  panel-warning</div>
  <div class="panel-body">
	<p>...</p></div>
 </div>
 <div class="panel panel-danger">
      <div class="panel-heading"> panel-danger</div>
  <div class="panel-body">
	<p>...</p></div>
   
  </div>

	  

	  <img src="https://1.gravatar.com/avatar/930438d1f943cc31755d87fb7f5474c5" alt="logo" class="img-rounded" width="76" height="76" /> <br/>
	  <img src="https://1.gravatar.com/avatar/930438d1f943cc31755d87fb7f5474c5" alt="logo" class="img-circle" width="76" height="76" /> <br/>
	  <img src="https://1.gravatar.com/avatar/930438d1f943cc31755d87fb7f5474c5" alt="logo" class="img-thumbnail" width="76" height="76" /><br/>

	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>
	  <br/>

	</div>



  </body>
</html>
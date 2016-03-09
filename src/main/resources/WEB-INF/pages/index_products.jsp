<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Electronics store - Products</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="/png/icon.png">
    <style>

        .navbar {
            margin-bottom: 10px;
            border-radius: 0;
        }

        .jumbotron {
            margin-bottom: 0;
            padding: 10px;

            position: relative;
            background-image: url('/png/Electronics.png');
            color: white;
        }

        footer {
            background-color: #f2f2f2;
            padding: 20px;
        }

        .center-panel
        {
            margin: 10px auto !important;
            float: none !important;
        }

    </style>
</head>
<body>

<div class="jumbotron">
    <div class="container text-center">
        <h1>Electronics Store</h1>
    </div>
</div>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="/"><span class="glyphicon glyphicon-home"></span></a></li>

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Products
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="dropdown-header" style="color:#337AB7;"><strong>Categories:</strong></li>
                        <li class="divider"></li>
                        <c:forEach items="${categories}" var="category">
                            <li><a href="/category/${category.id}">${category.name}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li><a href="/contact">Contact</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">

                <li><a href="/cart"><span class="glyphicon glyphicon-shopping-cart"></span> Cart </a></li>
                <li><a href="/edit"><span class="glyphicon glyphicon-wrench"></span> Settings </a></li>
                <c:choose>
                    <c:when test="${not empty pageContext.request.remoteUser}">
                        <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout </a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Sing In </a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<div class="container">

    <h3 style="margin-bottom: 0px;">Products:
    </h3>
</div>


<div class="container">
    <br>
    <c:forEach items="${products}" var="product">
    <div class="panel panel-info span5 center-panel" style="width:80%;">
        <div class="panel-heading" style="background-color: #f2f2f2"><strong>${product.name}</strong></div>
        <div class="row">
            <div class="col-sm-3">
                <div class="panel-body" style="margin-bottom: 0px;"><img src="/picture/${product.category.picture.id}" class="img-responsive" width="300" height="170" alt="Image"></div>
            </div>
            <div class="col-sm-5">
                <div class="panel-body"><p style="font-size:90%; margin-bottom: 5px;">${product.description}</p></div>
            </div>
            <div class="col-sm-4">
                <div class="panel-body">Price: <strong>$${product.price}</strong></div>
                <div class="panel-body"><form method="post" action="/product/buy/${product.id}"><button type="submit" class="btn btn-danger" style="text-align: center;">Add to Cart</button></form></div>
            </div>
        </div>
    </div>
        <br>
    </c:forEach>
</div>

<footer class="container-fluid text-center">
    <p>Store Search Bar</p>
    <form class="form-inline" role="search" action="/search" method="post">Looking for:
        <input type="text" class="form-control" name="pattern" size="50" placeholder="Item name">
        <button type="submit" class="btn btn-danger">Search</button>
    </form>
</footer>

<script>
    $('.dropdown-toggle').dropdown();

    $('#editpage').click(function(){
        window.location.href='/edit';
    })

    $( "li .searchterm" ).click(function() {
        console.log('testing');
    });
</script>

</body>
</html>
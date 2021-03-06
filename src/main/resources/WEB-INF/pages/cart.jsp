<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Electronics store - Cart</title>
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
                <li><a href="/about">About</a></li>
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

<c:if test="${not empty session_error}">
    <div class="container">
        <div class="alert alert-danger fade in" style="margin-top: 15px; margin-bottom: 0px;">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Error!</strong> ${session_error}
        </div>
    </div>
</c:if>

<c:choose>
    <c:when test="${empty cart}">
        <div class="container">
            <h3 style="margin-bottom: 400px;">Your shopping cart is empty.</h3>
        </div>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty error}">
            <div class="container">
                <div class="alert alert-danger fade in" style="margin-top: 15px; margin-bottom: 0px;">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Error!</strong> ${error}
                </div>
            </div>
        </c:if>

        <div class="container">
            <h3 style="margin-bottom: 30px;">Shopping Cart</h3>
        </div>

        <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Product name</th>
                    <th>Price</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cart}" var="product">
                    <tr>
                        <td>${product.name}</td>
                        <td>$${product.price}</td>
                        <td>
                            <form method="post" action="/cart/delete/${product.id}">
                                <button type="submit" class="btn btn-default"><span
                                        class="glyphicon glyphicon-remove"></span></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <h3>Total price: <b>$${price}</b></h3>

            <div class="container">
                <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/cart/pay"
                      method="post">
                    <div class="form-group"><h4>Personal information:</h4></div>
                    <div class="form-group" style="width:30%;"><input type="text" class="form-control" name="name"
                                                                      placeholder="Name"></div>
                    <div class="form-group" style="width:30%;"><input type="text" class="form-control" size="50"
                                                                      name="email" placeholder="E-mail"></div>
                    <div class="form-group" style="width:30%;"><input type="text" class="form-control" size="50"
                                                                      name="phone" placeholder="Phone number"></div>
                    <br>

                    <div class="form-group"><input type="submit" class="btn btn-primary" value="Submit & Pay"></div>
                    <br>
                </form>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<footer class="container-fluid text-center">
    <p>Store Search Bar</p>

    <form class="form-inline" role="search" action="/search" method="post">Looking for:
        <input type="text" class="form-control" name="pattern" size="50" placeholder="Item name">
        <button type="submit" class="btn btn-danger">Search</button>
    </form>
</footer>

</body>
</html>
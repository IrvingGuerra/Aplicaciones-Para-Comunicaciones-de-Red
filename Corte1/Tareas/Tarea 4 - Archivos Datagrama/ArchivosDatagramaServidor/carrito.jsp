<%-- 
    Document   : newjsp
    Created on : 14/06/2016, 02:44:45 PM
    Author     : Victor Adrian Sosa Hernandez <VASH>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/practica2.css">
        <title>Ecom</title>
    </head>
    <body>
        <div id="main">
            <div id="header">
                <div id="widgetBar">

                    <div class="headerWidget">
                        [ boton de revision ]
                    </div>

                    <div class="headerWidget">
                        [ carrito de compra ]
                    </div>

                </div>

                <a href="#">
                    <img src="#" id="logo" alt="Ecom logo">
                </a>

                <img src="#" id="logoText" alt="Ecom">
            </div>

            <div id="centerColumn">

                <p>su carrito contiene x articulos.</p>

                <div id="actionBar">
                    <a href="#" class="bubble hMargin">limpiar carrito</a>
                    <a href="#" class="bubble hMargin">continuar comprando</a>
                    <a href="#" class="bubble hMargin">proceder a la revision</a>
                </div>

                <h4 id="subtotal">[ subtotal: xxx ]</h4>

                <table id="cartTable">

                    <tr class="header">
                        <th>producto</th>
                        <th>nombre</th>
                        <th>precio</th>
                        <th>cantidad</th>
                    </tr>

                    <tr>
                        <td class="lightBlue">
                            <img src="#" alt="imagen del producto">
                        </td>
                        <td class="lightBlue">[ nombre del producto ]</td>
                        <td class="lightBlue">[ precio ]</td>
                        <td class="lightBlue">

                            <form action="updateCart" method="post">
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="1"
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="actualizar">
                            </form>
                        </td>
                    </tr>

                     <tr>
                        <td class="white">
                            <img src="#" alt="imagen del producto">
                        </td>
                        <td class="white">[ nombre del producto ]</td>
                        <td class="white">[ precio ]</td>
                        <td class="white">

                            <form action="updateCart" method="post">
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="1"
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="actualizar">
                            </form>
                        </td>
                    </tr>

                    <tr>
                        <td class="lightBlue">
                            <img src="#" alt="imagen del producto">
                        </td>
                        <td class="lightBlue">[ nombre del producto ]</td>
                        <td class="lightBlue">[ precio ]</td>
                        <td class="lightBlue">

                            <form action="updateCart" method="post">
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="1"
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="actualizar">
                            </form>
                        </td>
                    </tr>

                </table>

            </div>

            <div id="footer">
                <hr>
                <p id="footerText">[ texto del footer ]</p>
            </div>
        </div>
    </body>
</html>
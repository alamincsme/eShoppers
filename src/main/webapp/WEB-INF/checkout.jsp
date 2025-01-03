<%--
  Created by IntelliJ IDEA.
  User: Alamin
  Date: 13/11/2024
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <h3>Your Cart</h3>

    <div class="row">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th class="w-50">Name - Description</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>#</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="cartItem" items="${cart.cartItems}">
                    <tr>
                        <td>
                            <c:out value="${cartItem.product.name}"/>
                            -
                            <c:out value="${cartItem.product.description}"/>
                        </td>
                        <td>
                            <div class="btn-group" role="group">
                                <a class="btn btn-outline-warning" onclick="doAction(${cartItem.product.getId()}, 'remove')">-</a>
                                <button type="button" class="btn">
                                    <c:out value="${cartItem.quantity}"/>
                                </button>
                                <a class="btn btn-outline-success" onclick="doAction(${cartItem.product.getId()}, 'add')">+</a>
                            </div>
                        </td>
                        <td>
                            $ <c:out value="${cartItem.price}"/>
                        </td>
                        <td>
                            <a href="#" class="btn btn-outline-warning" onclick="doAction(${cartItem.product.getId()}, 'delete')">Remove</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td>
                        <h4>
                            Subtotal
                            (<c:out value="${cart.totalItem} items" />):
                            $<c:out value="${cart.totalPrice}"/>
                        </h4>
                    </td>
                    <td>
                        <a href="<c:url value="/home"/> " class="btn btn-outline-info">
                            Continue to Shopping
                        </a>
                    </td>

                    <td>
                        <a href="<c:url value="/order"/> " class="btn btn-outline-success text-right">
                            Proceed to Checkout
                        </a>
                    </td>
                </tr>
            </tfoot>
        </table>
    </div>
</div>

<script>
    function doAction(productId, action) {
      fetch("/add-to-cart", {
          method: 'POST' ,
          headers: {
              "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
          },
          body: "productId=" + productId + "&action="+action
      })
          .then((response) => {
              if (response.redirected) {
                  window.location.replace(response.url);
              }
          })
    }
</script>

<%--footer section--%>
<%@include file="includes/footer.jsp" %>
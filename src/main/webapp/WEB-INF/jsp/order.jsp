<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body {
        height: 400px;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
    }

    .welcome-h1 {
        font-size: 3.2rem;
    }

    .welcome-h2 {
        font-size: 2.8rem;
    }
</style>
<div class="content-body z-depth-1" data-page-name="Product #${order.id}">
    <h1 class="welcome-h1">${order.id}</h1>
    <h2 class="welcome-h2">${order.status}</h2>
</div>
<script>
</script>
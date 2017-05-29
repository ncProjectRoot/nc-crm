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

    .message-h1 {
        font-size: 3.2rem;
        text-align: center;
    }

    .message-h2 {
        font-size: 2.8rem;
        text-align: center;
    }

    @media (max-width: 700px)  {
        .message-h1 {
            font-size: 20px;
        }

        .message-h2 {
            font-size: 18px;
        }
    }
</style>
<div class="content-body z-depth-1" data-page-name="404">
    <h1 class="message-h1">404</h1>
    <h2 class="message-h2">page not found</h2>
</div>
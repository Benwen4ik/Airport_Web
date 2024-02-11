var headerText = `
<div id="block-body">

    <header>

        <!--     разметка для логотипа-->

        <div class="logo">
            <a href="index.html">
                <span class="use">USE</span>-<span class="web">WEB</span>.ru
            </a>
            <p>Разработка- это просто</p>
        </div>

        <!--     разметка для нашего меню-->

        <div class="top-menu">
            <ul>
                <li><a class="clickMenu" href="#">Темы</a></li>
                <li><a href="#">Видеоуроки</a></li>
                <li><a href="#">Об авторе</a></li>
                <li><a href="#">Обратная связь</a></li>
            </ul>
        </div>

        <!--     блок с авторизацией-->

        <div class="block-top-auth">
            <p><a href="#">Вход</a></p>
            <p><a href="#">Регистрация</a></p>
        </div>

    </header>
    </div>
`;
function setHeader() {
    var header =  document.createElement("div");
    header.innerHTML = headerText ;
    document.body.insertAdjacentElement('afterbegin', header );
}

setHeader();
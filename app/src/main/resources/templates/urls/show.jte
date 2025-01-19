@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.page(
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>Сайт:${page.getUrl().getName()}</h1>
            <table class="table table-bordered table-hover mt-3">
                <tbody>
                <tr>
                    <td>ID</td>
                    <td>${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${page.getUrl().getFormattedTimestamp()}</td>
                </tr>
                </tbody>
            </table>
            <h2 class="mt-5">Проверки</h2>
            <form method="post" action="/urls/{id}/checks">
                <button type="submit" class="btn btn-primary">Запустить проверку</button>
            </form>
            <table class="table table-bordered table-hover mt-3">
                <thead>
                    <tr><th class="col-1">ID</th>
                        <th class="col-1">Код ответа</th>
                        <th>title</th>
                        <th>h1</th>
                        <th>description</th>
                        <th class="col-2">Дата проверки</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </section>
`
)@import hexlet.code.dto.urls.UrlsPage
 @param UrlsPage page

 @template.layout.page(
 content = @`
     <section>
         @if(page.getFlash() != null)
             <div class="rounded-0 m-0 alert alert-dismissible fade show alert-success" role="alert">
                 <p class="m-0">${page.getFlash()}</p>
                 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
             </div>
         @endif
         <div class="container-lg mt-5">
             <h1>Сайты</h1>
                 <table class="table table-bordered table-hover mt-3">
                     <thead>
                         <tr>
                             <th class="col-1">ID</th>
                             <th>Имя</th>
                             <th class="col-2">Последняя проверка</th>
                             <th class="col-1">Код ответа</th>
                         </tr>
                     </thead>
                     <tbody>
                         @for(var url : page.getUrls())
                             <tr>
                                 <td>
                                     ${url.getId()}
                                 </td>
                                 <td>
                                     <a href="/urls/${url.getId()}">${url.getName()}</a>
                                 </td>
                                 <td>
                                     пока что тут ничего
                                     <!-- ${url.getLastCheck()} -->
                                 </td>
                                 <td>
                                     пока что тут ничего
                                     <!-- ${url.getCheckCode()} -->
                                 </td>
                             </tr>
                         @endfor
                     </tbody>
                 </table>
         </div>
     </section>
 `
 )
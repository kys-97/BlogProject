// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let articleId = document.getElementById('article-id').value;
        fetch(`/api/articles/${articleId}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('DELETE!');
                location.replace('/articles');
            });
    });
}

//수정 기능
const modifyButton = document.getElementById('modify-btn');
if (modifyButton) {
    //클릭 이벤트 감지 시 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('articleId');
        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Context-Type":"application/json",
            },
            body: JSON.stringify({
                articleTitle: document.getElementById('title').value,
                articleContent: document.getElementById('content').value
            })
        })
            .then(()=>{
                alert('UPDATE!');
                location.replace(`/articles/${id}`);
            })
    })
}

//등록 기능
const createButton = document.getElementById('create-btn');
if (createButton) {
    createButton.addEventListener('click', (event) => {
        fetch("/api/articles", {
            method:"POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                articleTitle: document.getElementById("title").value,
                articleContent: document.getElementById("content").value,
            }),
        })
            .then(()=>{
                alert("CREATE!");
                location.replace("/articles")
            });
    });
}
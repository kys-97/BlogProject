// 삭제 기능
const deleteButton = document.getElementById('delete-btn');
    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            let id = document.getElementById('article-id').value;
            fetch(`/api/articles/${id}`, {
                method: 'DELETE'
            })
                .then(()=>{
                    alert('DELETE!');
                    location.replace('/articles');
                });
        });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');
    if (modifyButton) {
        modifyButton.addEventListener('click', event => {
            let params = new URLSearchParams(location.search);
            let id = params.get('articleId');

            fetch(`/api/articles/${id}`, {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    articleTitle: document.getElementById('title').value,
                    articleContent: document.getElementById('content').value
                })
            })
                .then(()=>{
                    alert('UPDATE!');
                    location.replace(`/articles/${id}`);
                });
        });
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
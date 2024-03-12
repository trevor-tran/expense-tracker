function deleteTransactionById(transactionId) {
    const currentUrl = window.location.href;
    const url = `${currentUrl}/${transactionId}`;
    fetch(url, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        }
    });
}
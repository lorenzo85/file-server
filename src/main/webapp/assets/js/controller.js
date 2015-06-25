var deleteCallback = function (identifier) {
    $.get('delete/' + identifier, function () {
        refreshFilesList();
    });
};

function initialize() {
    $('#input-id').fileinput({
        uploadUrl: 'upload',
        showPreview: false
    }).on('filebatchuploadsuccess', function () {
        refreshFilesList();
    });
    refreshFilesList();
}

function refreshFilesList() {
    $.get("all", function (data) {
        populateFilesListItems(data);
    });
}

function populateFilesListItems(data) {
    React.render(
        <ListItems files={data} deleteCallback={ deleteCallback }/>,
        document.getElementById('well')
    );
}

initialize();
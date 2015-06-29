
var ListItem = React.createClass({
    render: function() {
        var text = this.props.text;
        var identifier = this.props.identifier;
        var deleteCallback = this.props.deleteCallback;
        return (<li className="list-group-item">
            <p className="list-group-item-text">
                <a className="btn btn-sm btn-primary btn-margin" href={ "load/" + identifier }>Download</a>
                <button className="btn btn-sm btn-danger btn-margin" onClick={ deleteCallback.bind(this, identifier)}>Delete</button>
                {text}
            </p>
        </li>);
    }});


var ListItems = React.createClass({
    render: function() {
        var files = this.props.files;
        var deleteCallback = this.props.deleteCallback;
        var rows = [];
        for (var key in files) {
            if (files.hasOwnProperty(key)) {
                var text = files[key].fileName;
                var identifier = files[key].id;
                rows.push(<ListItem text={text} identifier={identifier} deleteCallback={deleteCallback} />)
            }
        }
        return (<ul className="list-group">{rows}</ul>);
    }
});
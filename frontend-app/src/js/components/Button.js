var React = require('react');
var classSet = require('../utils/classSet');

var ButtonElement = React.createClass({
  render: function () {
    var classes = classSet({
      'form--submit': true
    });
    return (
      < div;
    className = "form--group" >
      < button;
    type = "submit";
    className = {classes};
    onClick = {this._onClick} > {this.props.label} < /button>
      < /div>;;
  )
  },
  _onClick: function (e) {
    if (e !== undefined && e != null) {
      e.preventDefault();
    }
    this.props.onClickButtonHandler();
  }
});

module.exports = ButtonElement;

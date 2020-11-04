var React = require('react');
var Validator = require('validatorjs');
var classSet = require('./utils/classSet');
var ProgressElement = require('./components/Progress');
var FormElement = require('./components/Form');
var ButtonElement = require('./components/Button');
var inputDatas = require('./datas/inputDatas');

var Content = React.createClass({
  getInitialState: function () {

    return {
      inputDatas: [],
      progressPercent: 0
    }

  },
  componentDidMount: function () {

    var inputDatas = this.props.inputDatas;
    this.setState({inputDatas: inputDatas});
    this._initialInputVerification();

  },
  render: function () {

    return (
      < div >
      < ProgressElement;
    percent = {this.state.progressPercent};
    />
    < ButtonElement;
    label = {'Daily Job'};
    onClickButtonHandler = {this._onClickDailyFormHandler};
    />
    < ButtonElement;
    label = {'INTERRUPT CURRENT'};
    onClickButtonHandler = {this._onClickInterruptHandler};
    />
    < FormElement;
    inputs = {this.state.inputDatas};
    onChangeInputHandler = {this._onChangeInputHandler};
    onSubmitFormHandler = {this._onSubmitFormHandler};
    percent = {this.state.progressPercent};
    />
    < /div>;;
  )
  },
  _initialInputVerification: function () {

    var self = this;
    this.state.inputDatas.forEach(function (item, index) {
      self._setAndValidateInput(index, item.value);
    });
    this._calculatePercent();

  },
  _resetInputDatas: function () {

    var inputDatas = this.state.inputDatas.map(function (item) {
      item.value = '';
      item.pristine = true;
      item.hasError = false;
      return item;
    });
    this.setState({inputDatas: inputDatas});
    this._initialInputVerification();

  },
  _calculatePercent: function () {

    var total = this.state.inputDatas.length;
    var done = 0;
    var progressPercent;
    this.state.inputDatas.forEach(function (item) {
      if (item.hasError === false) {
        done += 1;
      }
    });
    progressPercent = done / total * 100;
    this.setState({progressPercent: progressPercent});

  },
  _setAndValidateInput: function (index, value, noMorePristine) {

    var pristine = noMorePristine ? false : true;
    var inputDatas = this.state.inputDatas;
    var item = inputDatas[index];
    var data = {};
    var validation;

    inputDatas[index].value = value;
    inputDatas[index].pristine = pristine;
    inputDatas[index].hasError = false;
    inputDatas[index].errorMessage = '';

    data[item.id] = value || '';

    validation = new Validator(data, item.validation.rules, item.validation.messages);
    if (validation.fails()) {
      inputDatas[index].hasError = true;
      inputDatas[index].errorMessage = validation.errors.first(item.id);
    }
    this.setState({inputDatas: inputDatas});

  },
  _onChangeInputHandler: function (index, value) {

    this._setAndValidateInput(index, value, true);
    this._calculatePercent();

  },
  _onSubmitFormHandler: function () {

    get('/api/vote/custom?' + this.state.inputDatas)
      .then(res => res.json())
      .then((result) => {
          alert('success' + result)
        },
        (error) => {
          console.log('error _onSubmitFormHandler: ' + error)
        });
    if (this.state.progressPercent >= 100) {
      this._resetInputDatas();
      this._calculatePercent();
    }

  },
  _onClickDailyFormHandler: function () {

    get('/api/vote/daily')
      .then(res => res.json())
      .then((result) => {
          alert('success' + result)
        },
        (error) => {
          console.log('error _onClickDailyFormHandler: ' + error)
        });
    if (this.state.progressPercent >= 100) {
      this._resetInputDatas();
      this._calculatePercent();
    }

  },
  _onClickInterruptHandler: function () {

    get('/api/interrupt')
      .then(res => res.json())
      .then((result) => {
          alert('success' + result)
        },
        (error) => {
          console.log('error _onClickInterruptHandler: ' + error)
        });
    if (this.state.progressPercent >= 100) {
      this._resetInputDatas();
      this._calculatePercent();
    }

  }
});

React.render( < Content;
inputDatas = {inputDatas};
/>, document.body );;;

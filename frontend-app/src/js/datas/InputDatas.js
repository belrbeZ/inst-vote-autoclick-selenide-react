var datas = [
  {
    id: "Loops to repeat voting for Social network (i.e. Insta) *not implemented*",
    label: "loopsOfSocial",
    value: "",
    validation: {
      rules: {loopsOfSocial: ["integer"]},
      messages: {'integer.loopsOfSocial': 'Only Integer!'}
    },
    pristine: true,
    hasError: false,
    errorMessage: ''
  },
  {
    id: "Loops for launching Task type (i.e. Like, Subs etc.)",
    label: "loopsOfTaskType",
    value: "",
    validation: {
      rules: {loopsOfTaskType: ["required", "integer"]},
      messages: {"required.loopsOfTaskType": 'Required!', 'integer.loopsOfTaskType': 'Only Integer!'}
    },
    pristine: true,
    hasError: false,
    errorMessage: ''
  },
  {
    id: "Num of repeats for searching for tasks",
    label: "loopsOfTaskList",
    value: "",
    validation: {
      rules: {loopsOfTaskList: ["required", "integer"]},
      messages: {"required.loopsOfTaskList": 'Required!', 'integer.loopsOfTaskList': 'Integer!'}
    },
    pristine: true,
    hasError: false,
    errorMessage: ''
  }
];

module.exports = datas;

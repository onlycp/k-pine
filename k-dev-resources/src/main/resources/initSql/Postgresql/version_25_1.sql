alter table sys_task
    add task_argv text;

comment on column sys_task.task_argv is '任务参数';

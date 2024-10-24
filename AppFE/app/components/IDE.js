import React, { useState } from 'react';
import Editor from '@monaco-editor/react';

const IDE = ({ setSqlCode }) => {
  const [code, setCode] = useState(
    `--Question 1\n\n\n\n\n\n\n` +
    `--Question 2\n\n\n\n\n\n\n` +
    `--Question 3`
  );

  const handleEditorChange = (value) => {
    setCode(value);
    setSqlCode(value);// Pass the SQL code to the parent component
    // console.log(value)
  };

  return (
    <div className="mockup-window bg-base-300 border">
      <div className="bg-gray-900 flex justify-center px-4 py-5">
        <Editor
          height={300}
          language="sql"
          value={code}
          onChange={handleEditorChange}
          theme={"vs-dark"}
          selectOnLineNumbers={true}
          automaticLayout={true}
        />
      </div>
    </div>
  );
};

export default IDE;

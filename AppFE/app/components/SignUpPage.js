import React from 'react';
import { SignUp } from '@clerk/clerk-react';

const SignUpPage = () => {
  
  return (
    <div className="sign-up">
        <SignUp
          forceRedirectUrl="/" // URL to redirect to after successful sign-up
        />
    </div>
  );
};

export default SignUpPage;
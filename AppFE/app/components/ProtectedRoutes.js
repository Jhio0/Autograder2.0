import { showNotification } from "@mantine/notifications"; // Import useCustomer
import { useUser, SignIn } from "@clerk/clerk-react";

const ProtectedRoute = ({ element }) => {
    const { user, isLoaded, isSignedIn } = useUser();
  
    if (!isLoaded) {
      return <div>Loading...</div>;  // Show a loader while waiting for user data to load
    }
  
    if (!isSignedIn) {
      showNotification({
        id: 'auth-required',
        title: 'Authentication Required',
        message: 'You need to log in first before accessing this page.',
        color: 'red',
        autoClose: 8000,
        position: 'top-right',
      });
      return (
        <div className="sign-in"><SignIn /></div>
      );
    }
  
     
    return element;
  };
  
  
  export default ProtectedRoute;
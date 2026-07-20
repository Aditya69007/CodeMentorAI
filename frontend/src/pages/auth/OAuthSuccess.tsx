import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getCurrentUser } from "../../services/authService";

export default function OAuthSuccess() {

    const navigate = useNavigate();

    useEffect(() => {

        async function finishLogin() {

            const params =
                new URLSearchParams(window.location.search);

            const token = params.get("token");

            if (!token) {

                navigate("/login");

                return;
            }

            localStorage.setItem("token", token);

            try {

                const currentUser =
                    await getCurrentUser();

                const authUser = {

                    userId: currentUser.id,

                    firstName: currentUser.firstName,

                    lastName: currentUser.lastName,

                    email: currentUser.email,

                    role: currentUser.role,

                    profilePicture:
                        currentUser.profilePicture

                };

                localStorage.setItem(

                    "authUser",

                    JSON.stringify(authUser)

                );

                if (authUser.role === "ADMIN") {
                    window.location.href = "/admin/dashboard";

                } else {
                    
                    window.location.href = "/dashboard";

                }

            } catch (error) {

                console.error(error);

                localStorage.removeItem("token");

                localStorage.removeItem("authUser");

                navigate("/login");

            }

        }

        finishLogin();

    }, [navigate]);

    return (

        <div className="flex items-center justify-center h-screen">

            <h2 className="text-xl font-semibold">

                Signing in with Google...

            </h2>

        </div>

    );

}
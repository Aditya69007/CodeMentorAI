import { FiEdit2 } from "react-icons/fi";

export default function ProfileHero() {

  return (

    <section className="app-surface app-border rounded-3xl p-8">

      <div className="flex items-center justify-between">

        <div>

          <h1 className="text-3xl font-bold">
            My Profile
          </h1>

          <p className="app-text-secondary mt-2">
            Manage your personal information and developer identity.
          </p>

        </div>

        <button
          className="flex items-center gap-2 rounded-xl bg-blue-600 px-5 py-3 font-semibold text-white transition hover:bg-blue-700"
        >
          <FiEdit2 />

          Edit Profile

        </button>

      </div>

    </section>

  );

}
import api from "./api";
import type { SessionInfo } from "../components/security/types";

class SessionService {

  async getSessions(): Promise<SessionInfo[]> {
    const response = await api.get<SessionInfo[]>("/sessions");
    return response.data;
  }

  async logoutSession(sessionId: number) {
    await api.delete(`/sessions/${sessionId}`);
  }

  async logoutAll(sessionId: number) {
    await api.delete(`/sessions/logout-all/${sessionId}`);
  }

}

export default new SessionService();
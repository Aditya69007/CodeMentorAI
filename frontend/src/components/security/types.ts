export interface SessionInfo {
  id: number;
  deviceName: string;
  browser: string;
  operatingSystem: string;
  ipAddress: string;
  location: string;
  createdAt: string;
  lastSeen: string;
  expiresAt: string;
  isActive: boolean;
}

export interface SecuritySummaryData {
  totalDevices: number;
  activeDevices: number;
  currentDevice: string;
}
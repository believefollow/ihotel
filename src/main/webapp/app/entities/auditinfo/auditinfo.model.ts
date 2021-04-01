import * as dayjs from 'dayjs';

export interface IAuditinfo {
  id?: number;
  auditdate?: dayjs.Dayjs;
  audittime?: dayjs.Dayjs | null;
  empn?: string | null;
  aidentify?: string | null;
}

export class Auditinfo implements IAuditinfo {
  constructor(
    public id?: number,
    public auditdate?: dayjs.Dayjs,
    public audittime?: dayjs.Dayjs | null,
    public empn?: string | null,
    public aidentify?: string | null
  ) {}
}

export function getAuditinfoIdentifier(auditinfo: IAuditinfo): number | undefined {
  return auditinfo.id;
}

import * as dayjs from 'dayjs';

export interface IFwJywp {
  id?: number;
  jyrq?: dayjs.Dayjs | null;
  roomn?: string | null;
  guestname?: string | null;
  jywp?: string | null;
  fwy?: string | null;
  djr?: string | null;
  flag?: string | null;
  ghrq?: dayjs.Dayjs | null;
  djrq?: dayjs.Dayjs | null;
  remark?: string | null;
}

export class FwJywp implements IFwJywp {
  constructor(
    public id?: number,
    public jyrq?: dayjs.Dayjs | null,
    public roomn?: string | null,
    public guestname?: string | null,
    public jywp?: string | null,
    public fwy?: string | null,
    public djr?: string | null,
    public flag?: string | null,
    public ghrq?: dayjs.Dayjs | null,
    public djrq?: dayjs.Dayjs | null,
    public remark?: string | null
  ) {}
}

export function getFwJywpIdentifier(fwJywp: IFwJywp): number | undefined {
  return fwJywp.id;
}

import * as dayjs from 'dayjs';

export interface IFwWxf {
  id?: number;
  roomn?: string | null;
  memo?: string | null;
  djrq?: dayjs.Dayjs | null;
  wxr?: string | null;
  wcrq?: dayjs.Dayjs | null;
  djr?: string | null;
  flag?: string | null;
}

export class FwWxf implements IFwWxf {
  constructor(
    public id?: number,
    public roomn?: string | null,
    public memo?: string | null,
    public djrq?: dayjs.Dayjs | null,
    public wxr?: string | null,
    public wcrq?: dayjs.Dayjs | null,
    public djr?: string | null,
    public flag?: string | null
  ) {}
}

export function getFwWxfIdentifier(fwWxf: IFwWxf): number | undefined {
  return fwWxf.id;
}

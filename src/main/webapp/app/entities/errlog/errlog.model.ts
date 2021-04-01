import * as dayjs from 'dayjs';

export interface IErrlog {
  id?: number;
  iderrlog?: number;
  errnumber?: number | null;
  errtext?: string | null;
  errwindowmenu?: string | null;
  errobject?: string | null;
  errevent?: string | null;
  errline?: number | null;
  errtime?: dayjs.Dayjs | null;
  sumbitsign?: boolean | null;
  bmpfile?: string | null;
  bmpblobContentType?: string | null;
  bmpblob?: string | null;
}

export class Errlog implements IErrlog {
  constructor(
    public id?: number,
    public iderrlog?: number,
    public errnumber?: number | null,
    public errtext?: string | null,
    public errwindowmenu?: string | null,
    public errobject?: string | null,
    public errevent?: string | null,
    public errline?: number | null,
    public errtime?: dayjs.Dayjs | null,
    public sumbitsign?: boolean | null,
    public bmpfile?: string | null,
    public bmpblobContentType?: string | null,
    public bmpblob?: string | null
  ) {
    this.sumbitsign = this.sumbitsign ?? false;
  }
}

export function getErrlogIdentifier(errlog: IErrlog): number | undefined {
  return errlog.id;
}

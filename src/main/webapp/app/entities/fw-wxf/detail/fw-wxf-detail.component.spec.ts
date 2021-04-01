import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FwWxfDetailComponent } from './fw-wxf-detail.component';

describe('Component Tests', () => {
  describe('FwWxf Management Detail Component', () => {
    let comp: FwWxfDetailComponent;
    let fixture: ComponentFixture<FwWxfDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FwWxfDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fwWxf: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FwWxfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FwWxfDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fwWxf on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fwWxf).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

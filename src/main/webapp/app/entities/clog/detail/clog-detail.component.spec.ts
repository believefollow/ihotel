import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClogDetailComponent } from './clog-detail.component';

describe('Component Tests', () => {
  describe('Clog Management Detail Component', () => {
    let comp: ClogDetailComponent;
    let fixture: ComponentFixture<ClogDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ClogDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ clog: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ClogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load clog on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

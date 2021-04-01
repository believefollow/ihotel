import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CrinfoDetailComponent } from './crinfo-detail.component';

describe('Component Tests', () => {
  describe('Crinfo Management Detail Component', () => {
    let comp: CrinfoDetailComponent;
    let fixture: ComponentFixture<CrinfoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CrinfoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ crinfo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CrinfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CrinfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load crinfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.crinfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
